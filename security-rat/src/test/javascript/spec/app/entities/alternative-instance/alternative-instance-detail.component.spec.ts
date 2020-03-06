import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeInstanceDetailComponent } from 'app/entities/alternative-instance/alternative-instance-detail.component';
import { AlternativeInstance } from 'app/shared/model/alternative-instance.model';

describe('Component Tests', () => {
  describe('AlternativeInstance Management Detail Component', () => {
    let comp: AlternativeInstanceDetailComponent;
    let fixture: ComponentFixture<AlternativeInstanceDetailComponent>;
    const route = ({ data: of({ alternativeInstance: new AlternativeInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlternativeInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlternativeInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alternativeInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alternativeInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
