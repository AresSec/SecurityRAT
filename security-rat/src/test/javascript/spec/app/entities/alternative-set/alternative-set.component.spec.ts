import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeSetComponent } from 'app/entities/alternative-set/alternative-set.component';
import { AlternativeSetService } from 'app/entities/alternative-set/alternative-set.service';
import { AlternativeSet } from 'app/shared/model/alternative-set.model';

describe('Component Tests', () => {
  describe('AlternativeSet Management Component', () => {
    let comp: AlternativeSetComponent;
    let fixture: ComponentFixture<AlternativeSetComponent>;
    let service: AlternativeSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeSetComponent]
      })
        .overrideTemplate(AlternativeSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativeSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativeSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlternativeSet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alternativeSets && comp.alternativeSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
