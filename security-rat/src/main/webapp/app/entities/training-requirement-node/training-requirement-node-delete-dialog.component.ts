import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';
import { TrainingRequirementNodeService } from './training-requirement-node.service';

@Component({
  templateUrl: './training-requirement-node-delete-dialog.component.html'
})
export class TrainingRequirementNodeDeleteDialogComponent {
  trainingRequirementNode?: ITrainingRequirementNode;

  constructor(
    protected trainingRequirementNodeService: TrainingRequirementNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingRequirementNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingRequirementNodeListModification');
      this.activeModal.close();
    });
  }
}
