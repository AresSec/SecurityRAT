import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';
import { OptColumnContentService } from './opt-column-content.service';
import { OptColumnContentDeleteDialogComponent } from './opt-column-content-delete-dialog.component';

@Component({
  selector: 'jhi-opt-column-content',
  templateUrl: './opt-column-content.component.html'
})
export class OptColumnContentComponent implements OnInit, OnDestroy {
  optColumnContents?: IOptColumnContent[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected optColumnContentService: OptColumnContentService,
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
      this.optColumnContentService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOptColumnContent[]>) => (this.optColumnContents = res.body || []));
      return;
    }

    this.optColumnContentService.query().subscribe((res: HttpResponse<IOptColumnContent[]>) => (this.optColumnContents = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOptColumnContents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOptColumnContent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOptColumnContents(): void {
    this.eventSubscriber = this.eventManager.subscribe('optColumnContentListModification', () => this.loadAll());
  }

  delete(optColumnContent: IOptColumnContent): void {
    const modalRef = this.modalService.open(OptColumnContentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.optColumnContent = optColumnContent;
  }
}
