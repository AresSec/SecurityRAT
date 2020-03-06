import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';

@Component({
  selector: 'jhi-training-requirement-node-detail',
  templateUrl: './training-requirement-node-detail.component.html'
})
export class TrainingRequirementNodeDetailComponent implements OnInit {
  trainingRequirementNode: ITrainingRequirementNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingRequirementNode }) => (this.trainingRequirementNode = trainingRequirementNode));
  }

  previousState(): void {
    window.history.back();
  }
}
