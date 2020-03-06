import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISlideTemplate, SlideTemplate } from 'app/shared/model/slide-template.model';
import { SlideTemplateService } from './slide-template.service';

@Component({
  selector: 'jhi-slide-template-update',
  templateUrl: './slide-template-update.component.html'
})
export class SlideTemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    content: []
  });

  constructor(protected slideTemplateService: SlideTemplateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ slideTemplate }) => {
      this.updateForm(slideTemplate);
    });
  }

  updateForm(slideTemplate: ISlideTemplate): void {
    this.editForm.patchValue({
      id: slideTemplate.id,
      name: slideTemplate.name,
      description: slideTemplate.description,
      content: slideTemplate.content
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const slideTemplate = this.createFromForm();
    if (slideTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.slideTemplateService.update(slideTemplate));
    } else {
      this.subscribeToSaveResponse(this.slideTemplateService.create(slideTemplate));
    }
  }

  private createFromForm(): ISlideTemplate {
    return {
      ...new SlideTemplate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      content: this.editForm.get(['content'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISlideTemplate>>): void {
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
