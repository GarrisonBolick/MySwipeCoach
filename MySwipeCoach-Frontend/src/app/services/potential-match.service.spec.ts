import { TestBed } from '@angular/core/testing';

import { PotentialMatchesService } from './potential-match.service';

describe('PotentialMatchesService', () => {
  let service: PotentialMatchesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PotentialMatchesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
