import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITagInstance, TagInstance } from 'app/shared/model/tag-instance.model';
import { TagInstanceService } from './tag-instance.service';
import { ITagCategory } from 'app/shared/model/tag-category.model';
import { TagCategoryService } from 'app/entities/tag-category/tag-category.service';

@Component({
  selector: 'jhi-tag-instance-update',
  templateUrl: './tag-instance-update.component.html'
})
export class TagInstanceUpdateComponent implements OnInit {
  isSaving = false;
  tagcategories: ITagCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    tagCategory: []
  });

  constructor(
    protected tagInstanceService: TagInstanceService,
    protected tagCategoryService: TagCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagInstance }) => {
      this.updateForm(tagInstance);

      this.tagCategoryService.query().subscribe((res: HttpResponse<ITagCategory[]>) => (this.tagcategories = res.body || []));
    });
  }

  updateForm(tagInstance: ITagInstance): void {
    this.editForm.patchValue({
      id: tagInstance.id,
      name: tagInstance.name,
      description: tagInstance.description,
      showOrder: tagInstance.showOrder,
      active: tagInstance.active,
      tagCategory: tagInstance.tagCategory
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tagInstance = this.createFromForm();
    if (tagInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.tagInstanceService.update(tagInstance));
    } else {
      this.subscribeToSaveResponse(this.tagInstanceService.create(tagInstance));
    }
  }

  private createFromForm(): ITagInstance {
    return {
      ...new TagInstance(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      tagCategory: this.editForm.get(['tagCategory'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITagInstance>>): void {
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

  trackById(index: number, item: ITagCategory): any {
    return item.id;
  }
}
