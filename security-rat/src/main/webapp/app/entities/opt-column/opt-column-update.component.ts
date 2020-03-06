import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOptColumn, OptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from './opt-column.service';
import { IOptColumnType } from 'app/shared/model/opt-column-type.model';
import { OptColumnTypeService } from 'app/entities/opt-column-type/opt-column-type.service';

@Component({
  selector: 'jhi-opt-column-update',
  templateUrl: './opt-column-update.component.html'
})
export class OptColumnUpdateComponent implements OnInit {
  isSaving = false;
  optcolumntypes: IOptColumnType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    isVisibleByDefault: [],
    optColumnType: []
  });

  constructor(
    protected optColumnService: OptColumnService,
    protected optColumnTypeService: OptColumnTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optColumn }) => {
      this.updateForm(optColumn);

      this.optColumnTypeService.query().subscribe((res: HttpResponse<IOptColumnType[]>) => (this.optcolumntypes = res.body || []));
    });
  }

  updateForm(optColumn: IOptColumn): void {
    this.editForm.patchValue({
      id: optColumn.id,
      name: optColumn.name,
      description: optColumn.description,
      showOrder: optColumn.showOrder,
      active: optColumn.active,
      isVisibleByDefault: optColumn.isVisibleByDefault,
      optColumnType: optColumn.optColumnType
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optColumn = this.createFromForm();
    if (optColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.optColumnService.update(optColumn));
    } else {
      this.subscribeToSaveResponse(this.optColumnService.create(optColumn));
    }
  }

  private createFromForm(): IOptColumn {
    return {
      ...new OptColumn(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      isVisibleByDefault: this.editForm.get(['isVisibleByDefault'])!.value,
      optColumnType: this.editForm.get(['optColumnType'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptColumn>>): void {
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

  trackById(index: number, item: IOptColumnType): any {
    return item.id;
  }
}
