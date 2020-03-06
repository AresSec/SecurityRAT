import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingBranchNode } from 'app/shared/model/training-branch-node.model';

@Component({
  selector: 'jhi-training-branch-node-detail',
  templateUrl: './training-branch-node-detail.component.html'
})
export class TrainingBranchNodeDetailComponent implements OnInit {
  trainingBranchNode: ITrainingBranchNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingBranchNode }) => (this.trainingBranchNode = trainingBranchNode));
  }

  previousState(): void {
    window.history.back();
  }
}
