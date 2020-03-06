import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from './opt-column.service';

@Component({
  templateUrl: './opt-column-delete-dialog.component.html'
})
export class OptColumnDeleteDialogComponent {
  optColumn?: IOptColumn;

  constructor(protected optColumnService: OptColumnService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optColumnService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optColumnListModification');
      this.activeModal.close();
    });
  }
}
