import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';

@Component({
  selector: 'jhi-training-tree-node-detail',
  templateUrl: './training-tree-node-detail.component.html'
})
export class TrainingTreeNodeDetailComponent implements OnInit {
  trainingTreeNode: ITrainingTreeNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingTreeNode }) => (this.trainingTreeNode = trainingTreeNode));
  }

  previousState(): void {
    window.history.back();
  }
}
