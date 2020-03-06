import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusColumnValue } from 'app/shared/model/status-column-value.model';
import { StatusColumnValueService } from './status-column-value.service';

@Component({
  templateUrl: './status-column-value-delete-dialog.component.html'
})
export class StatusColumnValueDeleteDialogComponent {
  statusColumnValue?: IStatusColumnValue;

  constructor(
    protected statusColumnValueService: StatusColumnValueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusColumnValueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statusColumnValueListModification');
      this.activeModal.close();
    });
  }
}
