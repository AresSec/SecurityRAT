import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from './requirement-skeleton.service';

@Component({
  templateUrl: './requirement-skeleton-delete-dialog.component.html'
})
export class RequirementSkeletonDeleteDialogComponent {
  requirementSkeleton?: IRequirementSkeleton;

  constructor(
    protected requirementSkeletonService: RequirementSkeletonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requirementSkeletonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('requirementSkeletonListModification');
      this.activeModal.close();
    });
  }
}
