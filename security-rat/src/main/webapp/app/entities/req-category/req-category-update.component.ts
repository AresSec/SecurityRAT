import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReqCategory, ReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from './req-category.service';

@Component({
  selector: 'jhi-req-category-update',
  templateUrl: './req-category-update.component.html'
})
export class ReqCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    shortcut: [],
    description: [],
    showOrder: [],
    active: []
  });

  constructor(protected reqCategoryService: ReqCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reqCategory }) => {
      this.updateForm(reqCategory);
    });
  }

  updateForm(reqCategory: IReqCategory): void {
    this.editForm.patchValue({
      id: reqCategory.id,
      name: reqCategory.name,
      shortcut: reqCategory.shortcut,
      description: reqCategory.description,
      showOrder: reqCategory.showOrder,
      active: reqCategory.active
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reqCategory = this.createFromForm();
    if (reqCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.reqCategoryService.update(reqCategory));
    } else {
      this.subscribeToSaveResponse(this.reqCategoryService.create(reqCategory));
    }
  }

  private createFromForm(): IReqCategory {
    return {
      ...new ReqCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shortcut: this.editForm.get(['shortcut'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReqCategory>>): void {
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
