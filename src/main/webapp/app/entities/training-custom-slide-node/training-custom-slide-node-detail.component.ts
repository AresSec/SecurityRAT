import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

@Component({
  selector: 'jhi-training-custom-slide-node-detail',
  templateUrl: './training-custom-slide-node-detail.component.html'
})
export class TrainingCustomSlideNodeDetailComponent implements OnInit {
  trainingCustomSlideNode: ITrainingCustomSlideNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingCustomSlideNode }) => (this.trainingCustomSlideNode = trainingCustomSlideNode));
  }

  previousState(): void {
    window.history.back();
  }
}
