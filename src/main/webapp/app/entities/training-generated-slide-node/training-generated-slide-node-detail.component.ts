import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';

@Component({
  selector: 'jhi-training-generated-slide-node-detail',
  templateUrl: './training-generated-slide-node-detail.component.html'
})
export class TrainingGeneratedSlideNodeDetailComponent implements OnInit {
  trainingGeneratedSlideNode: ITrainingGeneratedSlideNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingGeneratedSlideNode }) => (this.trainingGeneratedSlideNode = trainingGeneratedSlideNode));
  }

  previousState(): void {
    window.history.back();
  }
}
