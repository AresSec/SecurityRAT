import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatusColumnValue, StatusColumnValue } from 'app/shared/model/status-column-value.model';
import { StatusColumnValueService } from './status-column-value.service';
import { IStatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from 'app/entities/status-column/status-column.service';

@Component({
  selector: 'jhi-status-column-value-update',
  templateUrl: './status-column-value-update.component.html'
})
export class StatusColumnValueUpdateComponent implements OnInit {
  isSaving = false;
  statuscolumns: IStatusColumn[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    statusColumn: []
  });

  constructor(
    protected statusColumnValueService: StatusColumnValueService,
    protected statusColumnService: StatusColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusColumnValue }) => {
      this.updateForm(statusColumnValue);

      this.statusColumnService.query().subscribe((res: HttpResponse<IStatusColumn[]>) => (this.statuscolumns = res.body || []));
    });
  }

  updateForm(statusColumnValue: IStatusColumnValue): void {
    this.editForm.patchValue({
      id: statusColumnValue.id,
      name: statusColumnValue.name,
      description: statusColumnValue.description,
      showOrder: statusColumnValue.showOrder,
      active: statusColumnValue.active,
      statusColumn: statusColumnValue.statusColumn
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusColumnValue = this.createFromForm();
    if (statusColumnValue.id !== undefined) {
      this.subscribeToSaveResponse(this.statusColumnValueService.update(statusColumnValue));
    } else {
      this.subscribeToSaveResponse(this.statusColumnValueService.create(statusColumnValue));
    }
  }

  private createFromForm(): IStatusColumnValue {
    return {
      ...new StatusColumnValue(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      statusColumn: this.editForm.get(['statusColumn'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusColumnValue>>): void {
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

  trackById(index: number, item: IStatusColumn): any {
    return item.id;
  }
}
