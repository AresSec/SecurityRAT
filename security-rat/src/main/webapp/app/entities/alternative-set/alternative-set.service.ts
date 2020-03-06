import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAlternativeSet } from 'app/shared/model/alternative-set.model';

type EntityResponseType = HttpResponse<IAlternativeSet>;
type EntityArrayResponseType = HttpResponse<IAlternativeSet[]>;

@Injectable({ providedIn: 'root' })
export class AlternativeSetService {
  public resourceUrl = SERVER_API_URL + 'api/alternative-sets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/alternative-sets';

  constructor(protected http: HttpClient) {}

  create(alternativeSet: IAlternativeSet): Observable<EntityResponseType> {
    return this.http.post<IAlternativeSet>(this.resourceUrl, alternativeSet, { observe: 'response' });
  }

  update(alternativeSet: IAlternativeSet): Observable<EntityResponseType> {
    return this.http.put<IAlternativeSet>(this.resourceUrl, alternativeSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlternativeSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlternativeSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlternativeSet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
