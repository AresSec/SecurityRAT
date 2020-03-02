import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';
import { TrainingCustomSlideNodeService } from './training-custom-slide-node.service';

@Component({
  templateUrl: './training-custom-slide-node-delete-dialog.component.html'
})
export class TrainingCustomSlideNodeDeleteDialogComponent {
  trainingCustomSlideNode?: ITrainingCustomSlideNode;

  constructor(
    protected trainingCustomSlideNodeService: TrainingCustomSlideNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingCustomSlideNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingCustomSlideNodeListModification');
      this.activeModal.close();
    });
  }
}
