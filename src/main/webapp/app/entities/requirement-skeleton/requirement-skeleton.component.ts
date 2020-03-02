import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from './requirement-skeleton.service';
import { RequirementSkeletonDeleteDialogComponent } from './requirement-skeleton-delete-dialog.component';

@Component({
  selector: 'jhi-requirement-skeleton',
  templateUrl: './requirement-skeleton.component.html'
})
export class RequirementSkeletonComponent implements OnInit, OnDestroy {
  requirementSkeletons?: IRequirementSkeleton[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected requirementSkeletonService: RequirementSkeletonService,
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
      this.requirementSkeletonService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IRequirementSkeleton[]>) => (this.requirementSkeletons = res.body || []));
      return;
    }

    this.requirementSkeletonService
      .query()
      .subscribe((res: HttpResponse<IRequirementSkeleton[]>) => (this.requirementSkeletons = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRequirementSkeletons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRequirementSkeleton): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRequirementSkeletons(): void {
    this.eventSubscriber = this.eventManager.subscribe('requirementSkeletonListModification', () => this.loadAll());
  }

  delete(requirementSkeleton: IRequirementSkeleton): void {
    const modalRef = this.modalService.open(RequirementSkeletonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.requirementSkeleton = requirementSkeleton;
  }
}
