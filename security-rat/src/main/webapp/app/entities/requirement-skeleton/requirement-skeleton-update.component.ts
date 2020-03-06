import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRequirementSkeleton, RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from './requirement-skeleton.service';
import { IReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from 'app/entities/req-category/req-category.service';
import { ITagInstance } from 'app/shared/model/tag-instance.model';
import { TagInstanceService } from 'app/entities/tag-instance/tag-instance.service';
import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from 'app/entities/collection-instance/collection-instance.service';
import { IProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from 'app/entities/project-type/project-type.service';

type SelectableEntity = IReqCategory | ITagInstance | ICollectionInstance | IProjectType;

type SelectableManyToManyEntity = ITagInstance | ICollectionInstance | IProjectType;

@Component({
  selector: 'jhi-requirement-skeleton-update',
  templateUrl: './requirement-skeleton-update.component.html'
})
export class RequirementSkeletonUpdateComponent implements OnInit {
  isSaving = false;
  reqcategories: IReqCategory[] = [];
  taginstances: ITagInstance[] = [];
  collectioninstances: ICollectionInstance[] = [];
  projecttypes: IProjectType[] = [];

  editForm = this.fb.group({
    id: [],
    universalId: [],
    shortName: [],
    description: [],
    showOrder: [],
    active: [],
    reqCategory: [],
    tagInstances: [],
    collectionInstances: [],
    projectTypes: []
  });

  constructor(
    protected requirementSkeletonService: RequirementSkeletonService,
    protected reqCategoryService: ReqCategoryService,
    protected tagInstanceService: TagInstanceService,
    protected collectionInstanceService: CollectionInstanceService,
    protected projectTypeService: ProjectTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requirementSkeleton }) => {
      this.updateForm(requirementSkeleton);

      this.reqCategoryService.query().subscribe((res: HttpResponse<IReqCategory[]>) => (this.reqcategories = res.body || []));

      this.tagInstanceService.query().subscribe((res: HttpResponse<ITagInstance[]>) => (this.taginstances = res.body || []));

      this.collectionInstanceService
        .query()
        .subscribe((res: HttpResponse<ICollectionInstance[]>) => (this.collectioninstances = res.body || []));

      this.projectTypeService.query().subscribe((res: HttpResponse<IProjectType[]>) => (this.projecttypes = res.body || []));
    });
  }

  updateForm(requirementSkeleton: IRequirementSkeleton): void {
    this.editForm.patchValue({
      id: requirementSkeleton.id,
      universalId: requirementSkeleton.universalId,
      shortName: requirementSkeleton.shortName,
      description: requirementSkeleton.description,
      showOrder: requirementSkeleton.showOrder,
      active: requirementSkeleton.active,
      reqCategory: requirementSkeleton.reqCategory,
      tagInstances: requirementSkeleton.tagInstances,
      collectionInstances: requirementSkeleton.collectionInstances,
      projectTypes: requirementSkeleton.projectTypes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requirementSkeleton = this.createFromForm();
    if (requirementSkeleton.id !== undefined) {
      this.subscribeToSaveResponse(this.requirementSkeletonService.update(requirementSkeleton));
    } else {
      this.subscribeToSaveResponse(this.requirementSkeletonService.create(requirementSkeleton));
    }
  }

  private createFromForm(): IRequirementSkeleton {
    return {
      ...new RequirementSkeleton(),
      id: this.editForm.get(['id'])!.value,
      universalId: this.editForm.get(['universalId'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      reqCategory: this.editForm.get(['reqCategory'])!.value,
      tagInstances: this.editForm.get(['tagInstances'])!.value,
      collectionInstances: this.editForm.get(['collectionInstances'])!.value,
      projectTypes: this.editForm.get(['projectTypes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequirementSkeleton>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
