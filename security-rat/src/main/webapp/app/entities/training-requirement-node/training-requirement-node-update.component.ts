import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingRequirementNode, TrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';
import { TrainingRequirementNodeService } from './training-requirement-node.service';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from 'app/entities/requirement-skeleton/requirement-skeleton.service';

type SelectableEntity = ITrainingTreeNode | IRequirementSkeleton;

@Component({
  selector: 'jhi-training-requirement-node-update',
  templateUrl: './training-requirement-node-update.component.html'
})
export class TrainingRequirementNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];
  requirementskeletons: IRequirementSkeleton[] = [];

  editForm = this.fb.group({
    id: [],
    node: [],
    requirementSkeleton: []
  });

  constructor(
    protected trainingRequirementNodeService: TrainingRequirementNodeService,
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected requirementSkeletonService: RequirementSkeletonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingRequirementNode }) => {
      this.updateForm(trainingRequirementNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));

      this.requirementSkeletonService
        .query()
        .subscribe((res: HttpResponse<IRequirementSkeleton[]>) => (this.requirementskeletons = res.body || []));
    });
  }

  updateForm(trainingRequirementNode: ITrainingRequirementNode): void {
    this.editForm.patchValue({
      id: trainingRequirementNode.id,
      node: trainingRequirementNode.node,
      requirementSkeleton: trainingRequirementNode.requirementSkeleton
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingRequirementNode = this.createFromForm();
    if (trainingRequirementNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingRequirementNodeService.update(trainingRequirementNode));
    } else {
      this.subscribeToSaveResponse(this.trainingRequirementNodeService.create(trainingRequirementNode));
    }
  }

  private createFromForm(): ITrainingRequirementNode {
    return {
      ...new TrainingRequirementNode(),
      id: this.editForm.get(['id'])!.value,
      node: this.editForm.get(['node'])!.value,
      requirementSkeleton: this.editForm.get(['requirementSkeleton'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingRequirementNode>>): void {
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
