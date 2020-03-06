import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';
import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from 'app/entities/collection-instance/collection-instance.service';
import { IProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from 'app/entities/project-type/project-type.service';

type SelectableEntity = IOptColumn | ICollectionInstance | IProjectType;

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html'
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;
  optcolumns: IOptColumn[] = [];
  collectioninstances: ICollectionInstance[] = [];
  projecttypes: IProjectType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    allRequirementsSelected: [],
    optColumns: [],
    collections: [],
    projectTypes: []
  });

  constructor(
    protected trainingService: TrainingService,
    protected optColumnService: OptColumnService,
    protected collectionInstanceService: CollectionInstanceService,
    protected projectTypeService: ProjectTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.updateForm(training);

      this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optcolumns = res.body || []));

      this.collectionInstanceService
        .query()
        .subscribe((res: HttpResponse<ICollectionInstance[]>) => (this.collectioninstances = res.body || []));

      this.projectTypeService.query().subscribe((res: HttpResponse<IProjectType[]>) => (this.projecttypes = res.body || []));
    });
  }

  updateForm(training: ITraining): void {
    this.editForm.patchValue({
      id: training.id,
      name: training.name,
      description: training.description,
      allRequirementsSelected: training.allRequirementsSelected,
      optColumns: training.optColumns,
      collections: training.collections,
      projectTypes: training.projectTypes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.createFromForm();
    if (training.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  private createFromForm(): ITraining {
    return {
      ...new Training(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      allRequirementsSelected: this.editForm.get(['allRequirementsSelected'])!.value,
      optColumns: this.editForm.get(['optColumns'])!.value,
      collections: this.editForm.get(['collections'])!.value,
      projectTypes: this.editForm.get(['projectTypes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
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
