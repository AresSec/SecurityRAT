import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

@Component({
  selector: 'jhi-requirement-skeleton-detail',
  templateUrl: './requirement-skeleton-detail.component.html'
})
export class RequirementSkeletonDetailComponent implements OnInit {
  requirementSkeleton: IRequirementSkeleton | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requirementSkeleton }) => (this.requirementSkeleton = requirementSkeleton));
  }

  previousState(): void {
    window.history.back();
  }
}
