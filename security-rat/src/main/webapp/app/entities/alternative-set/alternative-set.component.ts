import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlternativeSet } from 'app/shared/model/alternative-set.model';
import { AlternativeSetService } from './alternative-set.service';
import { AlternativeSetDeleteDialogComponent } from './alternative-set-delete-dialog.component';

@Component({
  selector: 'jhi-alternative-set',
  templateUrl: './alternative-set.component.html'
})
export class AlternativeSetComponent implements OnInit, OnDestroy {
  alternativeSets?: IAlternativeSet[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected alternativeSetService: AlternativeSetService,
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
      this.alternativeSetService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IAlternativeSet[]>) => (this.alternativeSets = res.body || []));
      return;
    }

    this.alternativeSetService.query().subscribe((res: HttpResponse<IAlternativeSet[]>) => (this.alternativeSets = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlternativeSets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlternativeSet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlternativeSets(): void {
    this.eventSubscriber = this.eventManager.subscribe('alternativeSetListModification', () => this.loadAll());
  }

  delete(alternativeSet: IAlternativeSet): void {
    const modalRef = this.modalService.open(AlternativeSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alternativeSet = alternativeSet;
  }
}
