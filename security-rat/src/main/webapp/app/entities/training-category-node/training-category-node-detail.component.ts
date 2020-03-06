import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingCategoryNode } from 'app/shared/model/training-category-node.model';

@Component({
  selector: 'jhi-training-category-node-detail',
  templateUrl: './training-category-node-detail.component.html'
})
export class TrainingCategoryNodeDetailComponent implements OnInit {
  trainingCategoryNode: ITrainingCategoryNode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingCategoryNode }) => (this.trainingCategoryNode = trainingCategoryNode));
  }

  previousState(): void {
    window.history.back();
  }
}
