import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from './project-type.service';
import { ProjectTypeDeleteDialogComponent } from './project-type-delete-dialog.component';

@Component({
  selector: 'jhi-project-type',
  templateUrl: './project-type.component.html'
})
export class ProjectTypeComponent implements OnInit, OnDestroy {
  projectTypes?: IProjectType[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected projectTypeService: ProjectTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.projectTypeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IProjectType[]>) => (this.projectTypes = res.body || []));
      return;
    }

    this.projectTypeService.query().subscribe((res: HttpResponse<IProjectType[]>) => (this.projectTypes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProjectTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProjectType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProjectTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('projectTypeListModification', () => this.loadAll());
  }

  delete(projectType: IProjectType): void {
    const modalRef = this.modalService.open(ProjectTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.projectType = projectType;
  }
}
