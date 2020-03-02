import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollectionCategory, CollectionCategory } from 'app/shared/model/collection-category.model';
import { CollectionCategoryService } from './collection-category.service';

@Component({
  selector: 'jhi-collection-category-update',
  templateUrl: './collection-category-update.component.html'
})
export class CollectionCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: []
  });

  constructor(
    protected collectionCategoryService: CollectionCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionCategory }) => {
      this.updateForm(collectionCategory);
    });
  }

  updateForm(collectionCategory: ICollectionCategory): void {
    this.editForm.patchValue({
      id: collectionCategory.id,
      name: collectionCategory.name,
      description: collectionCategory.description,
      showOrder: collectionCategory.showOrder,
      active: collectionCategory.active
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collectionCategory = this.createFromForm();
    if (collectionCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.collectionCategoryService.update(collectionCategory));
    } else {
      this.subscribeToSaveResponse(this.collectionCategoryService.create(collectionCategory));
    }
  }

  private createFromForm(): ICollectionCategory {
    return {
      ...new CollectionCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollectionCategory>>): void {
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
}
