import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingTreeNode, TrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from './training-tree-node.service';
import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from 'app/entities/training/training.service';

type SelectableEntity = ITrainingTreeNode | ITraining;

@Component({
  selector: 'jhi-training-tree-node-update',
  templateUrl: './training-tree-node-update.component.html'
})
export class TrainingTreeNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];
  trainings: ITraining[] = [];

  editForm = this.fb.group({
    id: [],
    nodeType: [],
    sortOrder: [],
    active: [],
    parentId: [],
    trainingId: []
  });

  constructor(
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected trainingService: TrainingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingTreeNode }) => {
      this.updateForm(trainingTreeNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));

      this.trainingService.query().subscribe((res: HttpResponse<ITraining[]>) => (this.trainings = res.body || []));
    });
  }

  updateForm(trainingTreeNode: ITrainingTreeNode): void {
    this.editForm.patchValue({
      id: trainingTreeNode.id,
      nodeType: trainingTreeNode.nodeType,
      sortOrder: trainingTreeNode.sortOrder,
      active: trainingTreeNode.active,
      parentId: trainingTreeNode.parentId,
      trainingId: trainingTreeNode.trainingId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingTreeNode = this.createFromForm();
    if (trainingTreeNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingTreeNodeService.update(trainingTreeNode));
    } else {
      this.subscribeToSaveResponse(this.trainingTreeNodeService.create(trainingTreeNode));
    }
  }

  private createFromForm(): ITrainingTreeNode {
    return {
      ...new TrainingTreeNode(),
      id: this.editForm.get(['id'])!.value,
      nodeType: this.editForm.get(['nodeType'])!.value,
      sortOrder: this.editForm.get(['sortOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      trainingId: this.editForm.get(['trainingId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingTreeNode>>): void {
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
}
