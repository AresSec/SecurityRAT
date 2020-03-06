import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnDetailComponent } from 'app/entities/status-column/status-column-detail.component';
import { StatusColumn } from 'app/shared/model/status-column.model';

describe('Component Tests', () => {
  describe('StatusColumn Management Detail Component', () => {
    let comp: StatusColumnDetailComponent;
    let fixture: ComponentFixture<StatusColumnDetailComponent>;
    const route = ({ data: of({ statusColumn: new StatusColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StatusColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statusColumn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statusColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
