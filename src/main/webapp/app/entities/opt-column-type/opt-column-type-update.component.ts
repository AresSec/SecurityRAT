import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOptColumnType, OptColumnType } from 'app/shared/model/opt-column-type.model';
import { OptColumnTypeService } from './opt-column-type.service';

@Component({
  selector: 'jhi-opt-column-type-update',
  templateUrl: './opt-column-type-update.component.html'
})
export class OptColumnTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected optColumnTypeService: OptColumnTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumnType }) => {
      this.updateForm(optColumnType);
    });
  }

  updateForm(optColumnType: IOptColumnType): void {
    this.editForm.patchValue({
      id: optColumnType.id,
      name: optColumnType.name,
      description: optColumnType.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optColumnType = this.createFromForm();
    if (optColumnType.id !== undefined) {
      this.subscribeToSaveResponse(this.optColumnTypeService.update(optColumnType));
    } else {
      this.subscribeToSaveResponse(this.optColumnTypeService.create(optColumnType));
    }
  }

  private createFromForm(): IOptColumnType {
    return {
      ...new OptColumnType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptColumnType>>): void {
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
