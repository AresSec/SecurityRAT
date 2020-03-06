import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';
import { AlternativeInstanceService } from './alternative-instance.service';
import { AlternativeInstanceDeleteDialogComponent } from './alternative-instance-delete-dialog.component';

@Component({
  selector: 'jhi-alternative-instance',
  templateUrl: './alternative-instance.component.html'
})
export class AlternativeInstanceComponent implements OnInit, OnDestroy {
  alternativeInstances?: IAlternativeInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected alternativeInstanceService: AlternativeInstanceService,
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
      this.alternativeInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IAlternativeInstance[]>) => (this.alternativeInstances = res.body || []));
      return;
    }

    this.alternativeInstanceService
      .query()
      .subscribe((res: HttpResponse<IAlternativeInstance[]>) => (this.alternativeInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlternativeInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlternativeInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlternativeInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('alternativeInstanceListModification', () => this.loadAll());
  }

  delete(alternativeInstance: IAlternativeInstance): void {
    const modalRef = this.modalService.open(AlternativeInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alternativeInstance = alternativeInstance;
  }
}
