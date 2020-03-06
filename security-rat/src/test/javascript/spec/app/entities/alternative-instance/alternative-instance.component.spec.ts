import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { AlternativeInstanceComponent } from 'app/entities/alternative-instance/alternative-instance.component';
import { AlternativeInstanceService } from 'app/entities/alternative-instance/alternative-instance.service';
import { AlternativeInstance } from 'app/shared/model/alternative-instance.model';

describe('Component Tests', () => {
  describe('AlternativeInstance Management Component', () => {
    let comp: AlternativeInstanceComponent;
    let fixture: ComponentFixture<AlternativeInstanceComponent>;
    let service: AlternativeInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [AlternativeInstanceComponent]
      })
        .overrideTemplate(AlternativeInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativeInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativeInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlternativeInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alternativeInstances && comp.alternativeInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
