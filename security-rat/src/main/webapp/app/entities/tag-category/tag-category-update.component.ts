import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITagCategory, TagCategory } from 'app/shared/model/tag-category.model';
import { TagCategoryService } from './tag-category.service';

@Component({
  selector: 'jhi-tag-category-update',
  templateUrl: './tag-category-update.component.html'
})
export class TagCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: []
  });

  constructor(protected tagCategoryService: TagCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagCategory }) => {
      this.updateForm(tagCategory);
    });
  }

  updateForm(tagCategory: ITagCategory): void {
    this.editForm.patchValue({
      id: tagCategory.id,
      name: tagCategory.name,
      description: tagCategory.description,
      showOrder: tagCategory.showOrder,
      active: tagCategory.active
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tagCategory = this.createFromForm();
    if (tagCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.tagCategoryService.update(tagCategory));
    } else {
      this.subscribeToSaveResponse(this.tagCategoryService.create(tagCategory));
    }
  }

  private createFromForm(): ITagCategory {
    return {
      ...new TagCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITagCategory>>): void {
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
