import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingBranchNode } from 'app/shared/model/training-branch-node.model';
import { TrainingBranchNodeService } from './training-branch-node.service';

@Component({
  templateUrl: './training-branch-node-delete-dialog.component.html'
})
export class TrainingBranchNodeDeleteDialogComponent {
  trainingBranchNode?: ITrainingBranchNode;

  constructor(
    protected trainingBranchNodeService: TrainingBranchNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingBranchNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingBranchNodeListModification');
      this.activeModal.close();
    });
  }
}
