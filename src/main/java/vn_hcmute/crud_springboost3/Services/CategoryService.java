package vn_hcmute.crud_springboost3.Services;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn_hcmute.crud_springboost3.Entity.Category;
import vn_hcmute.crud_springboost3.Reponsitory.CategoryRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByCategoryName(String name) {
        return Optional.empty();
    }

    @Override
    public <S extends Category> void save(S entity) {
        categoryRepository.save(entity);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(name, pageable);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }

    @Override
    public Page<Category> findByNameContaining(String keyword, Pageable page) {
        return categoryRepository.findByCategoryNameContaining(keyword, page);
    }


    @Override
    public Optional<Category> findById(Long aLong) {
        return categoryRepository.findById(aLong);
    }

    @Override
    public List<Category> findAllById(Iterable<Long> ids) {
        return List.of();
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public void delete(Category entity) {
        // Assuming you have a repository object named categoryRepository
        categoryRepository.delete(entity);
    }


    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }



}