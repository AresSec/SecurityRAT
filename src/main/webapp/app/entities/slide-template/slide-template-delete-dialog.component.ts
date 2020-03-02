import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISlideTemplate } from 'app/shared/model/slide-template.model';
import { SlideTemplateService } from './slide-template.service';

@Component({
  templateUrl: './slide-template-delete-dialog.component.html'
})
export class SlideTemplateDeleteDialogComponent {
  slideTemplate?: ISlideTemplate;

  constructor(
    protected slideTemplateService: SlideTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.slideTemplateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('slideTemplateListModification');
      this.activeModal.close();
    });
  }
}
