import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from './status-column.service';

@Component({
  templateUrl: './status-column-delete-dialog.component.html'
})
export class StatusColumnDeleteDialogComponent {
  statusColumn?: IStatusColumn;

  constructor(
    protected statusColumnService: StatusColumnService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusColumnService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statusColumnListModification');
      this.activeModal.close();
    });
  }
}
