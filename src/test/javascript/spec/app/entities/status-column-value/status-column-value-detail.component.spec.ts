import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { StatusColumnValueDetailComponent } from 'app/entities/status-column-value/status-column-value-detail.component';
import { StatusColumnValue } from 'app/shared/model/status-column-value.model';

describe('Component Tests', () => {
  describe('StatusColumnValue Management Detail Component', () => {
    let comp: StatusColumnValueDetailComponent;
    let fixture: ComponentFixture<StatusColumnValueDetailComponent>;
    const route = ({ data: of({ statusColumnValue: new StatusColumnValue(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [StatusColumnValueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StatusColumnValueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusColumnValueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statusColumnValue on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statusColumnValue).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
