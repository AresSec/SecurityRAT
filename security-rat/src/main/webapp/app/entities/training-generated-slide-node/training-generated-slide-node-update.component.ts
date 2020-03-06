import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingGeneratedSlideNode, TrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';
import { TrainingGeneratedSlideNodeService } from './training-generated-slide-node.service';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';

type SelectableEntity = ITrainingTreeNode | IOptColumn;

@Component({
  selector: 'jhi-training-generated-slide-node-update',
  templateUrl: './training-generated-slide-node-update.component.html'
})
export class TrainingGeneratedSlideNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];
  optcolumns: IOptColumn[] = [];

  editForm = this.fb.group({
    id: [],
    node: [],
    optColumn: []
  });

  constructor(
    protected trainingGeneratedSlideNodeService: TrainingGeneratedSlideNodeService,
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected optColumnService: OptColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingGeneratedSlideNode }) => {
      this.updateForm(trainingGeneratedSlideNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));

      this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optcolumns = res.body || []));
    });
  }

  updateForm(trainingGeneratedSlideNode: ITrainingGeneratedSlideNode): void {
    this.editForm.patchValue({
      id: trainingGeneratedSlideNode.id,
      node: trainingGeneratedSlideNode.node,
      optColumn: trainingGeneratedSlideNode.optColumn
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingGeneratedSlideNode = this.createFromForm();
    if (trainingGeneratedSlideNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingGeneratedSlideNodeService.update(trainingGeneratedSlideNode));
    } else {
      this.subscribeToSaveResponse(this.trainingGeneratedSlideNodeService.create(trainingGeneratedSlideNode));
    }
  }

  private createFromForm(): ITrainingGeneratedSlideNode {
    return {
      ...new TrainingGeneratedSlideNode(),
      id: this.editForm.get(['id'])!.value,
      node: this.editForm.get(['node'])!.value,
      optColumn: this.editForm.get(['optColumn'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingGeneratedSlideNode>>): void {
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
