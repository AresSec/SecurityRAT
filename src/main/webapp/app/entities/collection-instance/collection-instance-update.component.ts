import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollectionInstance, CollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from './collection-instance.service';
import { ICollectionCategory } from 'app/shared/model/collection-category.model';
import { CollectionCategoryService } from 'app/entities/collection-category/collection-category.service';

@Component({
  selector: 'jhi-collection-instance-update',
  templateUrl: './collection-instance-update.component.html'
})
export class CollectionInstanceUpdateComponent implements OnInit {
  isSaving = false;
  collectioncategories: ICollectionCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    collectionCategory: []
  });

  constructor(
    protected collectionInstanceService: CollectionInstanceService,
    protected collectionCategoryService: CollectionCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionInstance }) => {
      this.updateForm(collectionInstance);

      this.collectionCategoryService
        .query()
        .subscribe((res: HttpResponse<ICollectionCategory[]>) => (this.collectioncategories = res.body || []));
    });
  }

  updateForm(collectionInstance: ICollectionInstance): void {
    this.editForm.patchValue({
      id: collectionInstance.id,
      name: collectionInstance.name,
      description: collectionInstance.description,
      showOrder: collectionInstance.showOrder,
      active: collectionInstance.active,
      collectionCategory: collectionInstance.collectionCategory
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collectionInstance = this.createFromForm();
    if (collectionInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.collectionInstanceService.update(collectionInstance));
    } else {
      this.subscribeToSaveResponse(this.collectionInstanceService.create(collectionInstance));
    }
  }

  private createFromForm(): ICollectionInstance {
    return {
      ...new CollectionInstance(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      collectionCategory: this.editForm.get(['collectionCategory'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollectionInstance>>): void {
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

  trackById(index: number, item: ICollectionCategory): any {
    return item.id;
  }
}
