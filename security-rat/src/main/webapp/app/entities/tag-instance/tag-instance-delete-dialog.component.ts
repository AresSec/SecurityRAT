import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITagInstance } from 'app/shared/model/tag-instance.model';
import { TagInstanceService } from './tag-instance.service';

@Component({
  templateUrl: './tag-instance-delete-dialog.component.html'
})
export class TagInstanceDeleteDialogComponent {
  tagInstance?: ITagInstance;

  constructor(
    protected tagInstanceService: TagInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tagInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tagInstanceListModification');
      this.activeModal.close();
    });
  }
}
