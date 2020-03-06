import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnTypeDetailComponent } from 'app/entities/opt-column-type/opt-column-type-detail.component';
import { OptColumnType } from 'app/shared/model/opt-column-type.model';

describe('Component Tests', () => {
  describe('OptColumnType Management Detail Component', () => {
    let comp: OptColumnTypeDetailComponent;
    let fixture: ComponentFixture<OptColumnTypeDetailComponent>;
    const route = ({ data: of({ optColumnType: new OptColumnType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OptColumnTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptColumnTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optColumnType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optColumnType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
