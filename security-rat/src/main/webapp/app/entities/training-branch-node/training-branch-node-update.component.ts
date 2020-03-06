import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingBranchNode, TrainingBranchNode } from 'app/shared/model/training-branch-node.model';
import { TrainingBranchNodeService } from './training-branch-node.service';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';

@Component({
  selector: 'jhi-training-branch-node-update',
  templateUrl: './training-branch-node-update.component.html'
})
export class TrainingBranchNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    anchor: [],
    node: []
  });

  constructor(
    protected trainingBranchNodeService: TrainingBranchNodeService,
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingBranchNode }) => {
      this.updateForm(trainingBranchNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));
    });
  }

  updateForm(trainingBranchNode: ITrainingBranchNode): void {
    this.editForm.patchValue({
      id: trainingBranchNode.id,
      name: trainingBranchNode.name,
      anchor: trainingBranchNode.anchor,
      node: trainingBranchNode.node
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingBranchNode = this.createFromForm();
    if (trainingBranchNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingBranchNodeService.update(trainingBranchNode));
    } else {
      this.subscribeToSaveResponse(this.trainingBranchNodeService.create(trainingBranchNode));
    }
  }

  private createFromForm(): ITrainingBranchNode {
    return {
      ...new TrainingBranchNode(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      anchor: this.editForm.get(['anchor'])!.value,
      node: this.editForm.get(['node'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingBranchNode>>): void {
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

  trackById(index: number, item: ITrainingTreeNode): any {
    return item.id;
  }
}
