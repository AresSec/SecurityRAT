import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingCustomSlideNode, TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';
import { TrainingCustomSlideNodeService } from './training-custom-slide-node.service';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';

@Component({
  selector: 'jhi-training-custom-slide-node-update',
  templateUrl: './training-custom-slide-node-update.component.html'
})
export class TrainingCustomSlideNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    content: [],
    anchor: [],
    node: []
  });

  constructor(
    protected trainingCustomSlideNodeService: TrainingCustomSlideNodeService,
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingCustomSlideNode }) => {
      this.updateForm(trainingCustomSlideNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));
    });
  }

  updateForm(trainingCustomSlideNode: ITrainingCustomSlideNode): void {
    this.editForm.patchValue({
      id: trainingCustomSlideNode.id,
      name: trainingCustomSlideNode.name,
      content: trainingCustomSlideNode.content,
      anchor: trainingCustomSlideNode.anchor,
      node: trainingCustomSlideNode.node
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingCustomSlideNode = this.createFromForm();
    if (trainingCustomSlideNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingCustomSlideNodeService.update(trainingCustomSlideNode));
    } else {
      this.subscribeToSaveResponse(this.trainingCustomSlideNodeService.create(trainingCustomSlideNode));
    }
  }

  private createFromForm(): ITrainingCustomSlideNode {
    return {
      ...new TrainingCustomSlideNode(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      content: this.editForm.get(['content'])!.value,
      anchor: this.editForm.get(['anchor'])!.value,
      node: this.editForm.get(['node'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingCustomSlideNode>>): void {
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
