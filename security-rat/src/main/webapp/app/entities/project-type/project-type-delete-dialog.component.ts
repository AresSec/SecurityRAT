import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from './project-type.service';

@Component({
  templateUrl: './project-type-delete-dialog.component.html'
})
export class ProjectTypeDeleteDialogComponent {
  projectType?: IProjectType;

  constructor(
    protected projectTypeService: ProjectTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectTypeListModification');
      this.activeModal.close();
    });
  }
}
