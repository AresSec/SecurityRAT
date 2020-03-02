import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnDetailComponent } from 'app/entities/opt-column/opt-column-detail.component';
import { OptColumn } from 'app/shared/model/opt-column.model';

describe('Component Tests', () => {
  describe('OptColumn Management Detail Component', () => {
    let comp: OptColumnDetailComponent;
    let fixture: ComponentFixture<OptColumnDetailComponent>;
    const route = ({ data: of({ optColumn: new OptColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OptColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optColumn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
