package cap.libapp.services.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cap.libapp.dao.IBookDao;
import cap.libapp.dao.IBorrowingDao;
import cap.libapp.dao.IMembershipDao;
import cap.libapp.dto.BorrowingDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.entities.Book;
import cap.libapp.entities.Borrowing;
import cap.libapp.entities.Membership;
import cap.libapp.exceptions.AlreadyBorrowedException;
import cap.libapp.exceptions.BorrowingNotFoundException;
import cap.libapp.exceptions.MemberAlreadyExistsException;
import cap.libapp.exceptions.MemberNotFoundException;
import cap.libapp.exceptions.NoBookFoundException;
import cap.libapp.exceptions.NoBooksAvailableException;
import cap.libapp.exceptions.WrongPasswordException;
import cap.libapp.mapper.LibraryMapper;
import cap.libapp.services.IMembershipService;
import cap.libapp.util.Constants;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.LoginVm;
import cap.libapp.viewmodels.MemberDataVm;
import cap.libapp.viewmodels.ProfileVm;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
@Transactional
public class MembershipServiceImpl implements IMembershipService
{
	/* Implementation class for Membership Service interface for Library app.
	 * Author - Shrey.
	 */
	
	@Autowired
	private IMembershipDao membershipDao;
	
	@Autowired
	private IBookDao bookDao;
	
	@Autowired
	private IBorrowingDao borrowingDao;
	
	LibraryMapper libraryMapper = Mappers.getMapper(LibraryMapper.class);
	

	@Override
	public int addData(MembershipDto md) 
	{
		log.info("Method to add member");
		try
		{
			if(membershipDao.getAllUsernames().contains(md.getUsername())) 
				throw new MemberAlreadyExistsException(Constants.UEXISTS);
			
			return membershipDao.save(libraryMapper.toMembershipEntity(md)).getId();
		}
		catch(MemberAlreadyExistsException mae)
		{
			log.error("Member exists");
			throw mae;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}
	
	@Override
	public int updateData(int id, MembershipDto md) 
	{
		log.info("Method to update member");
		try
		{
			Optional<Membership> oMember= membershipDao.findById(id);
			if(!oMember.isPresent()) throw new MemberNotFoundException(Constants.NMF);
			Membership membership = oMember.get();
			membership.setName(md.getName());
			membership.setUsername(md.getUsername());
			membership.setPassword(md.getPassword());
			membership.setRoles(md.getRoles());
			membership.setPhone(md.getPhone());
			return membershipDao.save(membership).getId();
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}


	@Override
	public ProfileVm getMemberById(int id) 
	{
		log.info("Method to get member by id");
		try
		{
			Optional<Membership> oMember= membershipDao.findById(id);
			if(!oMember.isPresent()) throw new MemberNotFoundException(Constants.NMF);
			Membership m = oMember.get();
			return new ProfileVm(m.getUsername(), m.getPassword(), m.getName(), m.getPhone(), m.getRoles());
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public void deleteMembership(int id) 
	{
		log.info("Method to delete member");
		try
		{
			if(!membershipDao.findById(id).isPresent()) throw new MemberNotFoundException(Constants.NMF);
			membershipDao.deleteById(id);
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}
	
	@Override
	public Set<BorrowingVm> getAllBorrowings(int id) 
	{
		try
		{
			Optional<Membership> oMember = membershipDao.findById(id);
			if(!oMember.isPresent()) throw new MemberNotFoundException(Constants.NMF);
			Set<BorrowingVm> borrowList = new HashSet<>();
			for(Borrowing b : oMember.get().getBorrowings())
					borrowList.add(libraryMapper.toBorrowingVm(b));
			
			if(borrowList.isEmpty()) throw new BorrowingNotFoundException(Constants.BNF);
			return borrowList;
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(BorrowingNotFoundException bnf)
		{
			log.error(Constants.BNF);
			throw bnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}	

	@Override
	public boolean borrowBook(BorrowingDto borrowingdto) 
	{
		log.info("Method to borrow book");
		try
		{
			Book book = (bookDao.findById(borrowingdto.getIsbn()).isPresent())?bookDao.getById(borrowingdto.getIsbn()):null;
			
			if(book==null) throw new NoBookFoundException(Constants.NBF);
			if(book.getQuantityAvailable()<1) throw new NoBooksAvailableException(Constants.NBA);
			if(!membershipDao.findById(borrowingdto.getMembershipId()).isPresent())
				throw new MemberNotFoundException(Constants.NMF);
			
			Membership membership = membershipDao.getById(borrowingdto.getMembershipId());
			
			for(Borrowing b : membership.getBorrowings()) 
				if(b.getBook().equals(book)) throw new AlreadyBorrowedException(Constants.AB);
			
			Borrowing borrowing = new Borrowing();
			
			borrowing.setDateOfIssue(LocalDate.now());
			borrowing.setBook(book);
		
			if(membership.getBorrowings()==null) 
				membership.setBorrowings(new HashSet<>());
			
			borrowing.setId(Integer.parseInt(borrowingdto.getMembershipId()+"00"+book.getIsbn()));
			
			borrowingDao.save(borrowing);
			membership.getBorrowings().add(borrowing);
			membershipDao.save(membership);
			book.setQuantityAvailable(book.getQuantityAvailable()-1);
			bookDao.save(book);
			
			return true;
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(NoBookFoundException nbf)
		{
			log.error(Constants.NBF);
			throw nbf;
		}
		catch(NoBooksAvailableException nba)
		{
			log.error(Constants.NBA);
			throw nba;
		}
		catch(AlreadyBorrowedException ab)
		{
			log.error(Constants.AB);
			throw ab;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}

	@Override
	public boolean returnBook(BorrowingDto borrowingdto) 
	{
		log.info("Method to return book");
		try
		{
			Book book = (bookDao.findById(borrowingdto.getIsbn()).isPresent())?bookDao.getById(borrowingdto.getIsbn()):null;
			if(book==null)  throw new NoBookFoundException(Constants.NBF);
			
			if(!membershipDao.findById(borrowingdto.getMembershipId()).isPresent())
				throw new MemberNotFoundException(Constants.NMF);
			
			Membership membership = membershipDao.getById(borrowingdto.getMembershipId());
			
			for(Borrowing b: membership.getBorrowings())
			{
				if(b.getBook().equals(book)) membership.getBorrowings().remove(b);
			}
			
			membershipDao.save(membership);
			borrowingDao.deleteById(Integer.parseInt(borrowingdto.getMembershipId()+"00"+book.getIsbn()));
			book.setQuantityAvailable(book.getQuantityAvailable()+1);
			bookDao.save(book);
			
			return true;
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(NoBookFoundException nbf)
		{
			log.error(Constants.NBF);
			throw nbf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public double totalFine(int membershipId) 
	{
		log.info("Method to calculate fine");
		try
		{
			double fine = 0.0;
			Optional<Membership> oMember = membershipDao.findById(membershipId);
			if(!oMember.isPresent()) throw new MemberNotFoundException(Constants.NMF);
			Membership member = oMember.get();
			for(Borrowing b: member.getBorrowings())
			{
				fine = fine + b.getBook().getPrice()* Period.between(b.getDateOfIssue(), LocalDate.now()).getDays();
			}
			return fine;
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public LoginVm login(String username, String password) 
	{
		try
		{
			if(!membershipDao.getAllUsernames().contains(username)) throw new MemberNotFoundException(Constants.NMF);
			
			Membership m = membershipDao.findByUsername(username);
			if(!m.getPassword().equals(password)) throw new WrongPasswordException("Wrong Password.");
			
			if(m.getRoles().equals("ROLE_ADMIN")) return new LoginVm(m.getId(), true, true);
			return new LoginVm(m.getId(), false, true);
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(WrongPasswordException wpe)
		{
			log.error("wrong password");
			throw wpe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}

	@Override
	public List<MemberDataVm> getAllMembers() 
	{
		try
		{
			List<MemberDataVm> memberlist = new ArrayList<>();
			if(membershipDao.getAllUsernames().isEmpty()) throw new MemberNotFoundException(Constants.NMF);
			for(String s: membershipDao.getAllUsernames())
			{
				int id = membershipDao.findByUsername(s).getId();
				try
				{
					memberlist.add(new MemberDataVm(id, getMemberById(id), getAllBorrowings(id)));
				}
				catch (BorrowingNotFoundException bnfe) 
				{
					memberlist.add(new MemberDataVm(id, getMemberById(id), null));
				}
				
			}
			if(memberlist.isEmpty()) throw new MemberNotFoundException(Constants.NMF);
			return memberlist;
		}
		catch(MemberNotFoundException mnf)
		{
			log.error(Constants.NMF);
			throw mnf;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

}
