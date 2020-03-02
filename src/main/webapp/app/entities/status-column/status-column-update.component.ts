import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatusColumn, StatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from './status-column.service';

@Component({
  selector: 'jhi-status-column-update',
  templateUrl: './status-column-update.component.html'
})
export class StatusColumnUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    isEnum: [],
    showOrder: [],
    active: []
  });

  constructor(protected statusColumnService: StatusColumnService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusColumn }) => {
      this.updateForm(statusColumn);
    });
  }

  updateForm(statusColumn: IStatusColumn): void {
    this.editForm.patchValue({
      id: statusColumn.id,
      name: statusColumn.name,
      description: statusColumn.description,
      isEnum: statusColumn.isEnum,
      showOrder: statusColumn.showOrder,
      active: statusColumn.active
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusColumn = this.createFromForm();
    if (statusColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.statusColumnService.update(statusColumn));
    } else {
      this.subscribeToSaveResponse(this.statusColumnService.create(statusColumn));
    }
  }

  private createFromForm(): IStatusColumn {
    return {
      ...new StatusColumn(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      isEnum: this.editForm.get(['isEnum'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusColumn>>): void {
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
