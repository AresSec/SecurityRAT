import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITrainingCategoryNode, TrainingCategoryNode } from 'app/shared/model/training-category-node.model';
import { TrainingCategoryNodeService } from './training-category-node.service';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from 'app/entities/training-tree-node/training-tree-node.service';
import { IReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from 'app/entities/req-category/req-category.service';

type SelectableEntity = ITrainingTreeNode | IReqCategory;

@Component({
  selector: 'jhi-training-category-node-update',
  templateUrl: './training-category-node-update.component.html'
})
export class TrainingCategoryNodeUpdateComponent implements OnInit {
  isSaving = false;
  trainingtreenodes: ITrainingTreeNode[] = [];
  reqcategories: IReqCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    node: [],
    category: []
  });

  constructor(
    protected trainingCategoryNodeService: TrainingCategoryNodeService,
    protected trainingTreeNodeService: TrainingTreeNodeService,
    protected reqCategoryService: ReqCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingCategoryNode }) => {
      this.updateForm(trainingCategoryNode);

      this.trainingTreeNodeService.query().subscribe((res: HttpResponse<ITrainingTreeNode[]>) => (this.trainingtreenodes = res.body || []));

      this.reqCategoryService.query().subscribe((res: HttpResponse<IReqCategory[]>) => (this.reqcategories = res.body || []));
    });
  }

  updateForm(trainingCategoryNode: ITrainingCategoryNode): void {
    this.editForm.patchValue({
      id: trainingCategoryNode.id,
      name: trainingCategoryNode.name,
      node: trainingCategoryNode.node,
      category: trainingCategoryNode.category
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingCategoryNode = this.createFromForm();
    if (trainingCategoryNode.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingCategoryNodeService.update(trainingCategoryNode));
    } else {
      this.subscribeToSaveResponse(this.trainingCategoryNodeService.create(trainingCategoryNode));
    }
  }

  private createFromForm(): ITrainingCategoryNode {
    return {
      ...new TrainingCategoryNode(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      node: this.editForm.get(['node'])!.value,
      category: this.editForm.get(['category'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingCategoryNode>>): void {
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
