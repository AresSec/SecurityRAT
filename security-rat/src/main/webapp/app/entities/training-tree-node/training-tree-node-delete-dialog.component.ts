import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { TrainingTreeNodeService } from './training-tree-node.service';

@Component({
  templateUrl: './training-tree-node-delete-dialog.component.html'
})
export class TrainingTreeNodeDeleteDialogComponent {
  trainingTreeNode?: ITrainingTreeNode;

  constructor(
    protected trainingTreeNodeService: TrainingTreeNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingTreeNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingTreeNodeListModification');
      this.activeModal.close();
    });
  }
}
