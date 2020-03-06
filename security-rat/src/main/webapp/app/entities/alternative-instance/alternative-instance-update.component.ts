import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlternativeInstance, AlternativeInstance } from 'app/shared/model/alternative-instance.model';
import { AlternativeInstanceService } from './alternative-instance.service';
import { IAlternativeSet } from 'app/shared/model/alternative-set.model';
import { AlternativeSetService } from 'app/entities/alternative-set/alternative-set.service';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';

type SelectableEntity = IAlternativeSet | IRequirementSkeleton;

@Component({
  selector: 'jhi-alternative-instance-update',
  templateUrl: './alternative-instance-update.component.html'
})
export class AlternativeInstanceUpdateComponent implements OnInit {
  isSaving = false;
  alternativesets: IAlternativeSet[] = [];
  requirementskeletons: IRequirementSkeleton[] = [];

  editForm = this.fb.group({
    id: [],
    content: [],
    alternativeSet: [],
    requirementSkeleton: []
  });

  constructor(
    protected alternativeInstanceService: AlternativeInstanceService,
    protected alternativeSetService: AlternativeSetService,
    protected requirementSkeletonService: RequirementSkeletonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alternativeInstance }) => {
      this.updateForm(alternativeInstance);

      this.alternativeSetService.query().subscribe((res: HttpResponse<IAlternativeSet[]>) => (this.alternativesets = res.body || []));

      this.requirementSkeletonService
        .query()
        .subscribe((res: HttpResponse<IRequirementSkeleton[]>) => (this.requirementskeletons = res.body || []));
    });
  }

  updateForm(alternativeInstance: IAlternativeInstance): void {
    this.editForm.patchValue({
      id: alternativeInstance.id,
      content: alternativeInstance.content,
      alternativeSet: alternativeInstance.alternativeSet,
      requirementSkeleton: alternativeInstance.requirementSkeleton
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alternativeInstance = this.createFromForm();
    if (alternativeInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.alternativeInstanceService.update(alternativeInstance));
    } else {
      this.subscribeToSaveResponse(this.alternativeInstanceService.create(alternativeInstance));
    }
  }

  private createFromForm(): IAlternativeInstance {
    return {
      ...new AlternativeInstance(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      alternativeSet: this.editForm.get(['alternativeSet'])!.value,
      requirementSkeleton: this.editForm.get(['requirementSkeleton'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlternativeInstance>>): void {
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
}
