import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlternativeSet, AlternativeSet } from 'app/shared/model/alternative-set.model';
import { AlternativeSetService } from './alternative-set.service';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';

@Component({
  selector: 'jhi-alternative-set-update',
  templateUrl: './alternative-set-update.component.html'
})
export class AlternativeSetUpdateComponent implements OnInit {
  isSaving = false;
  optcolumns: IOptColumn[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    optColumn: []
  });

  constructor(
    protected alternativeSetService: AlternativeSetService,
    protected optColumnService: OptColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alternativeSet }) => {
      this.updateForm(alternativeSet);

      this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optcolumns = res.body || []));
    });
  }

  updateForm(alternativeSet: IAlternativeSet): void {
    this.editForm.patchValue({
      id: alternativeSet.id,
      name: alternativeSet.name,
      description: alternativeSet.description,
      showOrder: alternativeSet.showOrder,
      active: alternativeSet.active,
      optColumn: alternativeSet.optColumn
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alternativeSet = this.createFromForm();
    if (alternativeSet.id !== undefined) {
      this.subscribeToSaveResponse(this.alternativeSetService.update(alternativeSet));
    } else {
      this.subscribeToSaveResponse(this.alternativeSetService.create(alternativeSet));
    }
  }

  private createFromForm(): IAlternativeSet {
    return {
      ...new AlternativeSet(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      optColumn: this.editForm.get(['optColumn'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlternativeSet>>): void {
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

  trackById(index: number, item: IOptColumn): any {
    return item.id;
  }
}
