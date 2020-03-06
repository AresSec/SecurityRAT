import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeSetDetailComponent } from 'app/entities/alternative-set/alternative-set-detail.component';
import { AlternativeSet } from 'app/shared/model/alternative-set.model';

describe('Component Tests', () => {
  describe('AlternativeSet Management Detail Component', () => {
    let comp: AlternativeSetDetailComponent;
    let fixture: ComponentFixture<AlternativeSetDetailComponent>;
    const route = ({ data: of({ alternativeSet: new AlternativeSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlternativeSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlternativeSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alternativeSet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alternativeSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
