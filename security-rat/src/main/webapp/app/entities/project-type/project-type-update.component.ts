import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProjectType, ProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from './project-type.service';
import { IStatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from 'app/entities/status-column/status-column.service';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';

type SelectableEntity = IStatusColumn | IOptColumn;

@Component({
  selector: 'jhi-project-type-update',
  templateUrl: './project-type-update.component.html'
})
export class ProjectTypeUpdateComponent implements OnInit {
  isSaving = false;
  statuscolumns: IStatusColumn[] = [];
  optcolumns: IOptColumn[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    showOrder: [],
    active: [],
    statusColumns: [],
    optColumns: []
  });

  constructor(
    protected projectTypeService: ProjectTypeService,
    protected statusColumnService: StatusColumnService,
    protected optColumnService: OptColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectType }) => {
      this.updateForm(projectType);

      this.statusColumnService.query().subscribe((res: HttpResponse<IStatusColumn[]>) => (this.statuscolumns = res.body || []));

      this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optcolumns = res.body || []));
    });
  }

  updateForm(projectType: IProjectType): void {
    this.editForm.patchValue({
      id: projectType.id,
      name: projectType.name,
      description: projectType.description,
      showOrder: projectType.showOrder,
      active: projectType.active,
      statusColumns: projectType.statusColumns,
      optColumns: projectType.optColumns
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectType = this.createFromForm();
    if (projectType.id !== undefined) {
      this.subscribeToSaveResponse(this.projectTypeService.update(projectType));
    } else {
      this.subscribeToSaveResponse(this.projectTypeService.create(projectType));
    }
  }

  private createFromForm(): IProjectType {
    return {
      ...new ProjectType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      showOrder: this.editForm.get(['showOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      statusColumns: this.editForm.get(['statusColumns'])!.value,
      optColumns: this.editForm.get(['optColumns'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectType>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
