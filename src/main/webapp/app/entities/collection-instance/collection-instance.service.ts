import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICollectionInstance } from 'app/shared/model/collection-instance.model';

type EntityResponseType = HttpResponse<ICollectionInstance>;
type EntityArrayResponseType = HttpResponse<ICollectionInstance[]>;

@Injectable({ providedIn: 'root' })
export class CollectionInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/collection-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/collection-instances';

  constructor(protected http: HttpClient) {}

  create(collectionInstance: ICollectionInstance): Observable<EntityResponseType> {
    return this.http.post<ICollectionInstance>(this.resourceUrl, collectionInstance, { observe: 'response' });
  }

  update(collectionInstance: ICollectionInstance): Observable<EntityResponseType> {
    return this.http.put<ICollectionInstance>(this.resourceUrl, collectionInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollectionInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollectionInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
